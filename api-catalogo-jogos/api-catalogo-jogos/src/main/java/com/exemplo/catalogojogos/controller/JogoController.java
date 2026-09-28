package com.exemplo.catalogojogos.controller;

import com.exemplo.catalogojogos.model.Jogo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    private final List<Jogo> jogos = new ArrayList<>();
    private long proximoId = 1;

    @GetMapping
    public synchronized ResponseEntity<List<Jogo>> listar(
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String plataforma,
            @RequestParam(required = false) Boolean concluido) {

        List<Jogo> resultado = jogos.stream()
                .filter(jogo -> genero == null || jogo.getGenero().equalsIgnoreCase(genero))
                .filter(jogo -> plataforma == null || jogo.getPlataforma().equalsIgnoreCase(plataforma))
                .filter(jogo -> concluido == null || jogo.isConcluido() == concluido)
                .toList();

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public synchronized ResponseEntity<Jogo> buscarPorId(@PathVariable Long id) {
        Jogo jogo = encontrarJogo(id);
        if (jogo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jogo);
    }

    @PostMapping
    public synchronized ResponseEntity<Jogo> cadastrar(@RequestBody Jogo novoJogo) {
        if (!dadosValidos(novoJogo)) {
            return ResponseEntity.badRequest().build();
        }

        Jogo jogo = new Jogo(proximoId++, novoJogo.getNome(), novoJogo.getGenero(),
                novoJogo.getPlataforma(), novoJogo.isConcluido());
        jogos.add(jogo);
        return ResponseEntity.status(HttpStatus.CREATED).body(jogo);
    }

    @PutMapping("/{id}")
    public synchronized ResponseEntity<Jogo> atualizar(@PathVariable Long id, @RequestBody Jogo dados) {
        Jogo jogo = encontrarJogo(id);
        if (jogo == null) {
            return ResponseEntity.notFound().build();
        }
        if (!dadosValidos(dados)) {
            return ResponseEntity.badRequest().build();
        }

        jogo.setNome(dados.getNome());
        jogo.setGenero(dados.getGenero());
        jogo.setPlataforma(dados.getPlataforma());
        jogo.setConcluido(dados.isConcluido());
        return ResponseEntity.ok(jogo);
    }

    @DeleteMapping("/{id}")
    public synchronized ResponseEntity<Void> excluir(@PathVariable Long id) {
        Jogo jogo = encontrarJogo(id);
        if (jogo == null) {
            return ResponseEntity.notFound().build();
        }
        jogos.remove(jogo);
        return ResponseEntity.noContent().build();
    }

    private Jogo encontrarJogo(Long id) {
        return jogos.stream().filter(jogo -> jogo.getId().equals(id)).findFirst().orElse(null);
    }

    private boolean dadosValidos(Jogo jogo) {
        return jogo != null
                && jogo.getNome() != null && !jogo.getNome().isBlank()
                && jogo.getGenero() != null && !jogo.getGenero().isBlank()
                && jogo.getPlataforma() != null && !jogo.getPlataforma().isBlank();
    }
}
