package br.ufrn.imd.bancovestido.controller;

import br.ufrn.imd.bancovestido.DTO.ContaDTO;
import br.ufrn.imd.bancovestido.exception.ResourceNotFoundException;
import br.ufrn.imd.bancovestido.mapper.ContaMapper;
import br.ufrn.imd.bancovestido.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/conta")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService contaService;

    @GetMapping
    public List<ContaDTO> findAll() {
        return this.contaService.findAll().stream().map(ContaMapper::map).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public ContaDTO findOne(@PathVariable(name = "id") Long idConta) throws ResourceNotFoundException {
        return ContaMapper.map(this.contaService.findOne(idConta));
    }

    @PostMapping
    public Long save(@RequestBody ContaDTO conta) throws ResourceNotFoundException {
        return this.contaService.save(ContaMapper.map(conta)).getId();
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long idConta) throws ResourceNotFoundException {
        this.contaService.delete(idConta);
    }

    @PutMapping(path = "/{id}/credito")
    public void credit(@PathVariable(name = "id") Long idConta, @RequestParam(name = "valor") BigDecimal value) throws ResourceNotFoundException {
        this.contaService.credito(idConta, value);
    }

}
