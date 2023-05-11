package com.tms.lingofriends.repository;

import com.tms.lingofriends.model.EducationProduct;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationProductRepository extends JpaRepository<EducationProduct, Integer> {
}
