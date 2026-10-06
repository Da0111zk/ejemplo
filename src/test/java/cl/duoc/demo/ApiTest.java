package cl.duoc.demo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.net.URI;
import java.net.http.*;
import java.util.Date;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ApiTest {
    @Value("${local.server.port}") int port;
    @Value("${app.jwt.secret}") String secret;

    HttpResponse<String> send(String method, String path, String body, String token) throws Exception {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json");
        if (token != null) request.header("Authorization", "Bearer " + token);
        request.method(method, body == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body));
        return HttpClient.newHttpClient().send(request.build(), HttpResponse.BodyHandlers.ofString());
    }

    String login() throws Exception {
        var response = send("POST", "/auth/login", "{\"username\":\"admin\",\"password\":\"1234\"}", null);
        assertEquals(200, response.statusCode(), response.body());
        var matcher = Pattern.compile("\"token\"\\s*:\\s*\"([^\"]+)\"").matcher(response.body());
        assertTrue(matcher.find(), response.body());
        return matcher.group(1);
    }

    @Test void crudCompleto() throws Exception {
        String token = login();
        var created = send("POST", "/api/productos", "{\"nombre\":\"Teclado\",\"precio\":29990}", token);
        assertEquals(201, created.statusCode(), created.body());
        String location = created.headers().firstValue("Location").orElseThrow();
        assertEquals(200, send("GET", location, null, token).statusCode());
        assertEquals(200, send("PUT", location, "{\"nombre\":\"Mouse\",\"precio\":9990}", token).statusCode());
        assertTrue(send("GET", location, null, token).body().contains("Mouse"));
        assertEquals(204, send("DELETE", location, null, token).statusCode());
        assertEquals(404, send("GET", location, null, token).statusCode());
    }
    @Test void validacion() throws Exception { assertEquals(400, send("POST", "/api/productos", "{}", login()).statusCode()); }
    @Test void publico() throws Exception { assertEquals(200, send("GET", "/api/publico/estado", null, null).statusCode()); }
    @Test void sinToken() throws Exception { assertEquals(401, send("GET", "/api/productos", null, null).statusCode()); }
    @Test void credencialesInvalidas() throws Exception {
        assertEquals(401, send("POST", "/auth/login", "{\"username\":\"admin\",\"password\":\"mala\"}", null).statusCode());
    }
    @Test void tokenMalformado() throws Exception { assertEquals(401, send("GET", "/api/productos", null, "abc.def.ghi").statusCode()); }
    @Test void tokenExpirado() throws Exception {
        String token = Jwts.builder().subject("admin").expiration(new Date(System.currentTimeMillis()-60000))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))).compact();
        assertEquals(401, send("GET", "/api/productos", null, token).statusCode());
    }
    @Test void firmaIncorrecta() throws Exception {
        String token = Jwts.builder().subject("admin").expiration(new Date(System.currentTimeMillis()+60000))
                .signWith(Jwts.SIG.HS256.key().build()).compact();
        assertEquals(401, send("GET", "/api/productos", null, token).statusCode());
    }
}
