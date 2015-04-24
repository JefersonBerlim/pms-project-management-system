/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilitarios;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author jeferson
 */
public class Util implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    public Util() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Integer contadorObjetos(String objeto) {
        em = getEntityManager();
        Query listagem = em.createNamedQuery(objeto + ".findAll");
        return (listagem.getResultList().size()) + 1;

    }

    /**
     * Método para converter uma string
     *
     * @param hora
     * @return long 
     *
     * @throws ParseException
     */
    public Date stringEmHora(String hora) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = sdf.parse(hora);
        return date;

    }

}
