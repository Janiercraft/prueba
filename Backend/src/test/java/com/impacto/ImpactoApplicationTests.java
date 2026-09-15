package com.impacto;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
class ImpactoApplicationTests {
    @Test void applicationTargetsJava21() throws IOException {
        String resource = "/" + ImpactoApplicationTests.class.getName().replace('.', '/') + ".class";
        try (InputStream in = ImpactoApplicationTests.class.getResourceAsStream(resource)) {
            if (in == null) {
                fail("No se pudo localizar el bytecode de la prueba.");
            }
            byte[] header = in.readNBytes(8);
            assertEquals(8, header.length, "El archivo .class no tiene una cabecera válida.");
            int magic = ((header[0] & 0xFF) << 24) | ((header[1] & 0xFF) << 16) | ((header[2] & 0xFF) << 8) | (header[3] & 0xFF);
            int majorVersion = ((header[6] & 0xFF) << 8) | (header[7] & 0xFF);
            assertEquals(0xCAFEBABE, magic, "Cabecera de bytecode inválida.");
            assertEquals(65, majorVersion, "El proyecto debe compilar a bytecode Java 21.");
        }
    }
}
