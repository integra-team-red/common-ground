package cloudflight.integra.backend.hobbyGroup;


import cloudflight.integra.backend.emailSystem.UserJoinedGroupEvent;
import cloudflight.integra.backend.exceptions.AlreadyMemberOfThisHobbyGroupException;
import cloudflight.integra.backend.exceptions.NotMemberOfHobbyGroupException;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.location.model.Location;
import cloudflight.integra.backend.user.model.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class HobbyGroupService {
    private final HobbyGroupRepository repository;
    private final ApplicationEventPublisher eventPublisher;


    public HobbyGroupService(
        HobbyGroupRepository repository,
        ApplicationEventPublisher eventPublisher
    ) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public Page<HobbyGroup> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<HobbyGroup> getAllList(){ return repository.findAll(); }

    @Transactional(readOnly = true)
    public HobbyGroup getById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Page<HobbyGroup> getAllByLocation(Location location, Pageable pageable) {
        return repository.findByGroupLocation(location, pageable);
    }

    @Transactional
    public HobbyGroup create(HobbyGroup group) {
        group.setId(null);
        return repository.save(group);
    }

    @Transactional
    public HobbyGroup update(UUID id, HobbyGroup newGroup) {

        HobbyGroup oldGroup = repository.findById(id).orElseThrow();
        oldGroup.setName(newGroup.getName());
        oldGroup.setDescription(newGroup.getDescription());
        oldGroup.setRadiusKm(newGroup.getRadiusKm());

        return oldGroup;
    }

    @Transactional
    public boolean delete(UUID id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        } else throw new NoSuchElementException();
    }

    @Transactional(readOnly = true)
    public Page<HobbyGroup> filterByName(String containedString, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(containedString, pageable);
    }

    @Transactional
    public HobbyGroup addNewMemberToHobbyGroup(User newMember, UUID hobbyGroupId) {
        HobbyGroup hobbyGroup = repository.findById(hobbyGroupId).orElseThrow();
        if (hobbyGroup.getMembers().contains(newMember))
            throw new AlreadyMemberOfThisHobbyGroupException("The user is already part of this group");
        hobbyGroup.getMembers().add(newMember);
        repository.save(hobbyGroup);
        eventPublisher.publishEvent(new UserJoinedGroupEvent(newMember, hobbyGroup));
        return hobbyGroup;
    }

    @Transactional
    public HobbyGroup deleteMemberFromHobbyGroup(User oldMember, UUID hobbyGroupId) {
        HobbyGroup hobbyGroup = repository.findById(hobbyGroupId).orElseThrow();
        if (!hobbyGroup.getMembers().contains(oldMember))
            throw new NotMemberOfHobbyGroupException("The user is not part of this group");
        hobbyGroup.getMembers().remove(oldMember);
        repository.save(hobbyGroup);
        return hobbyGroup;
    }

}
