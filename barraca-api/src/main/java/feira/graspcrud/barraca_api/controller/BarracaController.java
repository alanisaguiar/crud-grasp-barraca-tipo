package feira.graspcrud.barraca_api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import feira.graspcrud.barraca_api.dto.BarracaRequest;
import feira.graspcrud.barraca_api.dto.BarracaResponse;
import feira.graspcrud.barraca_api.service.BarracaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/barracas")
public class BarracaController {

    private final BarracaService barracaService;

    public BarracaController(BarracaService barracaService) {
        this.barracaService = barracaService;
    }

    @GetMapping
    public ResponseEntity<List<BarracaResponse>> listar() {
        return ResponseEntity.ok(barracaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarracaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(barracaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<BarracaResponse> cadastrar(
            @RequestBody @Valid BarracaRequest request,
            UriComponentsBuilder uriBuilder) {

        BarracaResponse criada = barracaService.cadastrar(request);

        URI uri = uriBuilder.path("/api/barracas/{id}")
                .buildAndExpand(criada.id())
                .toUri();

        return ResponseEntity.created(uri).body(criada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        barracaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}