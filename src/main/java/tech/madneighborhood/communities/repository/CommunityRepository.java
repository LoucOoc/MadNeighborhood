package tech.madneighborhood.communities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.madneighborhood.communities.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {

    Community findByName(String name);

}
