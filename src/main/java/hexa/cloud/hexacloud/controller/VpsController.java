package hexa.cloud.hexacloud.controller;

import hexa.cloud.hexacloud.dto.request.VpsRequestDTO;
import hexa.cloud.hexacloud.dto.request.response.VpsResponseDTO;
import hexa.cloud.hexacloud.model.Vps;
import hexa.cloud.hexacloud.service.VpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vps")
public class VpsController {

    @Autowired
    private VpsService vpsService;

    @GetMapping
    public List<VpsResponseDTO> getAll() {
        return vpsService.getAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public VpsResponseDTO getById(@PathVariable Integer id) {
        return toDTO(vpsService.getById(id));
    }

    @PostMapping
    public VpsResponseDTO create(@RequestBody VpsRequestDTO dto) {
        Vps vps = new Vps();
        vps.setName(dto.getName());
        vps.setIpAddress(dto.getIpAddress());
        vps.setStatus(dto.getStatus());
        vps.setOs_type(dto.getOsType());
        vps.setOwnerId(dto.getOwnerId());
        return toDTO(vpsService.create(vps));
    }

    @PutMapping("/{id}")
    public VpsResponseDTO update(@PathVariable Integer id, @RequestBody VpsRequestDTO dto) {
        Vps vps = new Vps();
        vps.setName(dto.getName());
        vps.setIpAddress(dto.getIpAddress());
        vps.setStatus(dto.getStatus());
        vps.setOs_type(dto.getOsType());
        vps.setOwnerId(dto.getOwnerId());
        return toDTO(vpsService.update(id, vps));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        vpsService.delete(id);
    }

    private VpsResponseDTO toDTO(Vps vps) {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return new VpsResponseDTO(
            Long.valueOf(vps.getId()),
            vps.getName(),
            vps.getIpAddress(),
            vps.getStatus(),
            vps.getOs_type(),
            vps.getCreatedAt() != null ? vps.getCreatedAt().format(fmt) : null,
            vps.getUpdatedAt() != null ? vps.getUpdatedAt().format(fmt) : null,
            null // Nếu muốn trả về user, cần join thêm
        );
    }
}