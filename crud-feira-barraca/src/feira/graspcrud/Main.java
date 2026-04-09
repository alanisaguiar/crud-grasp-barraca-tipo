package feira.graspcrud;

import java.util.Scanner;
import feira.graspcrud.controller.BarracaController;
import feira.graspcrud.repository.BarracaRepository;
import feira.graspcrud.repository.TipoBarracaRepository;
import feira.graspcrud.repository.json.BarracaRepositoryJson;
import feira.graspcrud.repository.json.TipoBarracaRepositoryJson;
import feira.graspcrud.service.BarracaService;
import feira.graspcrud.service.TipoBarracaService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BarracaRepository barracaRepository = new BarracaRepositoryJson();
        TipoBarracaRepository tipoBarracaRepository = new TipoBarracaRepositoryJson();

        TipoBarracaService tipoBarracaService =
                new TipoBarracaService(tipoBarracaRepository, barracaRepository);

        BarracaService barracaService =
                new BarracaService(barracaRepository, tipoBarracaRepository);

        BarracaController controller =
                new BarracaController(scanner, barracaService, tipoBarracaService);

        controller.iniciarMenu();

        scanner.close();
    }
}