package companyregistry.persistence;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.omg.CORBA.SystemException;

import companyregistry.restservice.exception.ServerErrorResourceException;

public class SqlSessionFactoryProvider<M> {

    private static final  String MYBATIS_CONF_FILE = "config.xml";
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private M mapper;
    
    public static SqlSessionFactory produceSessionFactory() {
    	try {
    		Reader myBatisConfReader = Resources.getResourceAsReader(MYBATIS_CONF_FILE);
    		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(myBatisConfReader);
    		return sqlSessionFactory;
    	} catch (IOException | PersistenceException e) {
            throw new ServerErrorResourceException("problem trying to read mybatis config file: ", e);
        }
    }
    
    public M openSessionAndInitMapper(Class<M> mapperClass) throws SystemException {
        try {
        	sqlSessionFactory = SqlSessionFactoryProvider.produceSessionFactory();
            sqlSession = sqlSessionFactory.openSession();
            this.mapper = sqlSession.getMapper(mapperClass);
        } catch (Exception e) {
            throw new ServerErrorResourceException(e.getMessage());
        }
        return mapper;
    }
    
    public void closeSession() {
    	if (sqlSession != null) {
    		sqlSession.close();
    	}
    }
    
    public void closeSessionAndCommit() {
    	if (sqlSession != null) {
    		sqlSession.commit();
    		sqlSession.close();
    	}
    }
    
    public void closeSessionAndRollback() {
        if (sqlSession != null) {
        	sqlSession.rollback();
            sqlSession.close();
        }
    }
    
}
