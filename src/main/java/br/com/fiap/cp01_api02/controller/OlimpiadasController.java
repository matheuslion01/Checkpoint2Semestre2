package br.com.fiap.cp01_api02.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.cp01_api02.model.Olimpiadas;
import br.com.fiap.cp01_api02.repository.OlimpiadasRepository;

@RestController
@RequestMapping("/olimpiadas")
public class OlimpiadasController {
    @Autowired
    private OlimpiadasRepository repository;

     @PostMapping
    public ResponseEntity<Olimpiadas> create(@RequestBody Olimpiadas olimpiadas) {         
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(olimpiadas));
    }

    @GetMapping("/{id}")    
    public ResponseEntity<Olimpiadas> findById(@PathVariable Long id) { 
        return repository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());        
    }

    @GetMapping    
    public ResponseEntity<List<Olimpiadas>> findAll() {        
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Olimpiadas> update(@PathVariable Long id, 
                                @RequestBody Olimpiadas olimpiadas) {

        Optional<Olimpiadas> optOlimpiadas = repository.findById(id);

        if (optOlimpiadas.isPresent()) {
            olimpiadas.setId(id);
            Olimpiadas olimpiadasAlterado = repository.save(olimpiadas);
            return ResponseEntity.ok(olimpiadasAlterado);
        } else {
            return ResponseEntity.notFound().build();
        }     
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) { 
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
 
    }
}
