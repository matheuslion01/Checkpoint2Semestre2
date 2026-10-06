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

import br.com.fiap.cp01_api02.model.Brasileirao;
import br.com.fiap.cp01_api02.repository.BrasileiraoRepository;

@RestController
@RequestMapping("/campeonatobrasileiro")
public class BrasileiraoController {
    
    @Autowired
    private BrasileiraoRepository repository;

     @PostMapping
    public ResponseEntity<Brasileirao> create(@RequestBody Brasileirao brasileirao) {         
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(brasileirao));
    }

    @GetMapping("/{id}")    
    public ResponseEntity<Brasileirao> findById(@PathVariable Long id) { 
        return repository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());        
    }

    @GetMapping    
    public ResponseEntity<List<Brasileirao>> findAll() {        
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brasileirao> update(@PathVariable Long id, 
                                @RequestBody Brasileirao brasileirao) {

        Optional<Brasileirao> optBrasileirao = repository.findById(id);

        if (optBrasileirao.isPresent()) {
            brasileirao.setId(id);
            Brasileirao brasileiraoAlterado = repository.save(brasileirao);
            return ResponseEntity.ok(brasileiraoAlterado);
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
