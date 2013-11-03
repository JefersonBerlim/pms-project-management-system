/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilitarios;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author berlim
 */
public class Conexao{

    private EntityManagerFactory emf;
        
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public Conexao(){
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");

    }

}
