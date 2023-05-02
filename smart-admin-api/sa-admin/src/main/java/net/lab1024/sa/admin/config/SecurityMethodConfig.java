package net.lab1024.sa.admin.config;

import net.lab1024.sa.admin.module.system.login.domain.LoginEmployeeDetail;
import net.lab1024.sa.common.common.annoation.SaAuth;
import net.lab1024.sa.common.common.security.SecurityMethodSource;
import net.lab1024.sa.common.common.security.SecurityPermissionCheckService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.ExpressionBasedAnnotationAttributeFactory;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityMethodConfig extends GlobalMethodSecurityConfiguration {

    @Bean(SaAuth.saAuth)
    public SecurityPermissionCheckService securityPermissionCheckService() {
        return new SecurityPermissionCheckService() {
            @Override
            public boolean checkPermission(Authentication authentication, String permission) {
                LoginEmployeeDetail loginEmployeeDetail = (LoginEmployeeDetail) authentication.getPrincipal();
                if (loginEmployeeDetail.getAdministratorFlag()) {
                    return true;
                }
                return super.permissionJudge(loginEmployeeDetail, permission);
            }
        };
    }

    @Override
    public MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        ExpressionBasedAnnotationAttributeFactory attributeFactory = new ExpressionBasedAnnotationAttributeFactory(this.getExpressionHandler());
        return new SecurityMethodSource(attributeFactory, SaAuth.saAuth);
    }
}
