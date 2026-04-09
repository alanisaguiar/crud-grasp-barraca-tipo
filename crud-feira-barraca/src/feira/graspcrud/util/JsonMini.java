package feira.graspcrud.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonMini {

    public String ler(String caminho) {
        try {
            Path path = Path.of(caminho);
            if (!Files.exists(path)) {
                return "";
            }
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo JSON: " + caminho, e);
        }
    }

    public void escrever(String caminho, String conteudo) {
        try {
            Path path = Path.of(caminho);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.writeString(path, conteudo, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao escrever arquivo JSON: " + caminho, e);
        }
    }
}