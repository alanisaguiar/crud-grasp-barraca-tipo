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

import feira.graspcrud.barraca_api.dto.TipoBarracaRequest;
import feira.graspcrud.barraca_api.dto.TipoBarracaResponse;
import feira.graspcrud.barraca_api.service.TipoBarracaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tipos-barraca")
public class TipoBarracaController {

    private final TipoBarracaService tipoBarracaService;

    public TipoBarracaController(TipoBarracaService tipoBarracaService) {
        this.tipoBarracaService = tipoBarracaService;
    }

    @GetMapping
    public ResponseEntity<List<TipoBarracaResponse>> listar() {
        return ResponseEntity.ok(tipoBarracaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoBarracaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipoBarracaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TipoBarracaResponse> cadastrar(
            @RequestBody @Valid TipoBarracaRequest request,
            UriComponentsBuilder uriBuilder) {

        TipoBarracaResponse criado = tipoBarracaService.cadastrar(request);

        URI uri = uriBuilder.path("/api/tipos-barraca/{id}")
                .buildAndExpand(criado.id())
                .toUri();

        return ResponseEntity.created(uri).body(criado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        tipoBarracaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}