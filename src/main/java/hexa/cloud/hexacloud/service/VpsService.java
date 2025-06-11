package hexa.cloud.hexacloud.service;

import hexa.cloud.hexacloud.model.Vps;
import hexa.cloud.hexacloud.repository.VpsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VpsService {
    @Autowired
    private VpsRepository vpsRepository;

    public List<Vps> getAll() {
        return vpsRepository.findAll();
    }

    public Vps getById(Integer id) {
        return vpsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VPS not found"));
    }

    public Vps create(Vps vps) {
        return vpsRepository.save(vps);
    }

    public Vps update(Integer id, Vps vpsUpdate) {
        Vps vps = getById(id);
        vps.setName(vpsUpdate.getName());
        vps.setIpAddress(vpsUpdate.getIpAddress());
        vps.setStatus(vpsUpdate.getStatus());
        vps.setOs_type(vpsUpdate.getOs_type());
        vps.setOwnerId(vpsUpdate.getOwnerId());
        return vpsRepository.save(vps);
    }

    public void delete(Integer id) {
        vpsRepository.deleteById(id);
    }
}
