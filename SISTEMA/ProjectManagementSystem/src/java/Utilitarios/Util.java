/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilitarios;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
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

    public Date stringHoraEmDate(String hora) throws ParseException {

        Calendar calendario = Calendar.getInstance();
        Date data = new Date();
        int nHora;
        int nMinuto;

        nHora = Integer.parseInt(hora.substring(0, 2));
        nMinuto = Integer.parseInt(hora.substring(3, 5));

        calendario.setTime(data);
        calendario.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, nHora, nMinuto);

        data = calendario.getTime();

        return data;

    }

}
