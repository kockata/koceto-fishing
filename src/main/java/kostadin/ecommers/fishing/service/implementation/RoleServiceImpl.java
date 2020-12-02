package kostadin.ecommers.fishing.service.implementation;

import kostadin.ecommers.fishing.domain.entities.Role;
import kostadin.ecommers.fishing.domain.models.service.RoleServiceModel;
import kostadin.ecommers.fishing.repository.RoleRepository;
import kostadin.ecommers.fishing.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAndFlush(new Role("ROLE_USER"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
            this.roleRepository.saveAndFlush(new Role("ROLE_SUPERUSER"));
        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository
                .findAll()
                .stream()
                .map(role -> this.modelMapper.map(role, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return modelMapper.map(this.roleRepository.findByAuthority(authority), RoleServiceModel.class);
    }
}
