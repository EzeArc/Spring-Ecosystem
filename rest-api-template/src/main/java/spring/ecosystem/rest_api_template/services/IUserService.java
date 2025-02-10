package spring.ecosystem.rest_api_template.services;

import spring.ecosystem.rest_api_template.entities.User;

public interface IUserService {
    User findOneByEmail(String email);
}
