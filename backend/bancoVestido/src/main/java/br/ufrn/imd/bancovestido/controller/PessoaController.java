package br.ufrn.imd.bancovestido.controller;

import br.ufrn.imd.bancovestido.DTO.PessoaDTO;
import br.ufrn.imd.bancovestido.exception.ResourceNotFoundException;
import br.ufrn.imd.bancovestido.mapper.PessoaMapper;
import br.ufrn.imd.bancovestido.model.Pessoa;
import br.ufrn.imd.bancovestido.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pessoa")
@RequiredArgsConstructor
public class PessoaController {
    private final PessoaService pessoaService;

    @GetMapping
    public List<PessoaDTO> findAll() {
        return this.pessoaService.findAll().stream().map(PessoaMapper::map).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public PessoaDTO findOne(@PathVariable(name = "id") Long idPessoa) throws ResourceNotFoundException {
        return PessoaMapper.map(this.pessoaService.findOne(idPessoa));
    }

    @PostMapping
    public Long save(@RequestBody Pessoa pessoa) throws ResourceNotFoundException {
        return this.pessoaService.save(pessoa).getId();
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long idPessoa) throws ResourceNotFoundException {
        this.pessoaService.delete(idPessoa);
    }
}
