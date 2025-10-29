package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.SocialAuthAccount;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocialAuthAccountRepository extends JpaRepository<SocialAuthAccount, Integer> {
    List<SocialAuthAccount> findByUser_UserId(Integer userId);
    Optional<SocialAuthAccount> findByProviderAndProviderUserId(
        SocialAuthAccount.Provider provider, 
        String providerUserId
    );
    Optional<SocialAuthAccount> findByProviderAndProviderEmail(
        SocialAuthAccount.Provider provider, 
        String providerEmail
    );
}

