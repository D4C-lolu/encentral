package D4C.encentral.dao.user;



import D4C.encentral.dto.mapper.user.AdminMapper;
import D4C.encentral.dto.user.admin.AdminCreationDTO;
import D4C.encentral.dto.user.admin.AdminDTO;
import D4C.encentral.model.user.QAdmin;
import D4C.encentral.model.user.Admin;
import D4C.util.HibernateUtil;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;


/**
 * DAO Class for Admin object
 */
public class AdminDAOImpl implements UserDAO<AdminDTO, AdminCreationDTO> {

    private static EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    private QAdmin admin;

    private AdminMapper adminMapper;


    public AdminDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.admin = QAdmin.admin;
        this.adminMapper = AdminMapper.INSTANCE;

    }

    @Override
    public Optional<AdminDTO> get(long regNo, String password)  {
        Preconditions.checkArgument(regNo>0,"Invalid User Id");
        Preconditions.checkNotNull(regNo);
        Admin a = queryFactory.selectFrom(admin)
                .where(admin.regNo.eq(regNo))
                .fetchOne();
        AdminDTO aDTO =adminMapper.adminToDTO(a);
        return Optional.ofNullable(aDTO);
    }

    @Override
    public List<AdminDTO> getAll() {
        List<Admin> admins =  queryFactory.selectFrom(admin)
                .orderBy(admin.regNo.asc())
                .fetch();
        List<AdminDTO> adminDTOList = new ArrayList<>();
        for(Admin a:admins){
            adminDTOList.add(adminMapper.adminToDTO(a));
        }
        return adminDTOList;
    }

    @Override
    public Optional<AdminDTO> get(long regNo) {
        Preconditions.checkArgument(regNo > 0, "Invalid User Id");
        Preconditions.checkNotNull(regNo);
        Admin a = queryFactory.selectFrom(admin)
                .where(admin.regNo.eq(regNo))
                .fetchOne();
        AdminDTO sDTO = adminMapper.adminToDTO(a);
        return Optional.ofNullable(sDTO);
    }

    @Override
    public void save(AdminCreationDTO adminCreationDTO) {

        Preconditions.checkNotNull(adminCreationDTO,"Invalid argument");
        Preconditions.checkArgument(adminCreationDTO.getRegNo()>0,"Invalid Registration number");

        Admin a = adminMapper.creationDTOtoAdmin(adminCreationDTO);
        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.insert(admin).set(admin.firstName, a.getFirstName())
                    .set(admin.lastName, a.getLastName())
                    .set(admin.password, a.getPassword())
                    .set(admin.regNo, a.getRegNo())
                    .execute();
        });
    }



    @Override
    public void updateName(Long regNo, String firstname,String lastname) {

        Preconditions.checkNotNull(regNo,"Invalid argument");
        Preconditions.checkArgument(regNo >0);
        Preconditions.checkNotNull(firstname);
        Preconditions.checkNotNull(lastname);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.update(admin)
                    .where(admin.regNo.eq(regNo))
                    .set(admin.firstName, firstname)
                    .set(admin.lastName,lastname)
                    .execute();
        });
    }

    @Override
    public void updatePassword(Long regNo, String password) {

        Preconditions.checkNotNull(regNo,"Invalid argument");
        Preconditions.checkArgument(regNo >0);
        Preconditions.checkNotNull(password);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.update(admin)
                    .where(admin.regNo.eq(regNo))
                    .set(admin.password, password)
                    .execute();
        });
    }


    @Override
    public void delete(Long regNo) {

        Preconditions.checkNotNull(regNo,"Invalid argument");
        Preconditions.checkArgument(regNo >0);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.delete(admin)
                    .where(admin.regNo.eq(regNo))
                    .execute();
        });
    }
}
