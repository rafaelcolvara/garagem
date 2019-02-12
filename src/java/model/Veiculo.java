/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.jdbc.Blob;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "veiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Veiculo.findAll", query = "SELECT v FROM Veiculo v")
    , @NamedQuery(name = "Veiculo.findByIdveiculo", query = "SELECT v FROM Veiculo v WHERE v.idveiculo = :idveiculo")
    , @NamedQuery(name = "Veiculo.findByMarca", query = "SELECT v FROM Veiculo v WHERE v.marca = :marca")
    , @NamedQuery(name = "Veiculo.findByModelo", query = "SELECT v FROM Veiculo v WHERE v.modelo = :modelo")
    , @NamedQuery(name = "Veiculo.findByAnofabricacao", query = "SELECT v FROM Veiculo v WHERE v.anofabricacao = :anofabricacao")
    , @NamedQuery(name = "Veiculo.findByAnomodelo", query = "SELECT v FROM Veiculo v WHERE v.anomodelo = :anomodelo")
    , @NamedQuery(name = "Veiculo.findByPlaca", query = "SELECT v FROM Veiculo v WHERE v.placa = :placa")})
public class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idveiculo")
    private Integer idveiculo;
    @Column(name = "marca")
    private String marca;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "anofabricacao")
    private String anofabricacao;
    @Column(name = "anomodelo")
    private String anomodelo;
    @Column(name = "placa")
    private String placa;
    @Column(name = "imagem")
    private byte[] imagem;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente clienteIdcliente;
    @JoinColumn(name = "lojista_idlogista", referencedColumnName = "idlogista")
    @ManyToOne(optional = false)
    private Lojista lojistaIdlogista;

    public Veiculo() {
    }

    public Veiculo(Integer idveiculo) {
        this.idveiculo = idveiculo;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }



    public Integer getIdveiculo() {
        return idveiculo;
    }

    public void setIdveiculo(Integer idveiculo) {
        this.idveiculo = idveiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnofabricacao() {
        return anofabricacao;
    }

    public void setAnofabricacao(String anofabricacao) {
        this.anofabricacao = anofabricacao;
    }

    public String getAnomodelo() {
        return anomodelo;
    }

    public void setAnomodelo(String anomodelo) {
        this.anomodelo = anomodelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Cliente getClienteIdcliente() {
        return clienteIdcliente;
    }

    public void setClienteIdcliente(Cliente clienteIdcliente) {
        this.clienteIdcliente = clienteIdcliente;
    }

    public Lojista getLojistaIdlogista() {
        return lojistaIdlogista;
    }

    public void setLojistaIdlogista(Lojista lojistaIdlogista) {
        this.lojistaIdlogista = lojistaIdlogista;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idveiculo != null ? idveiculo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Veiculo)) {
            return false;
        }
        Veiculo other = (Veiculo) object;
        return !((this.idveiculo == null && other.idveiculo != null) || (this.idveiculo != null && !this.idveiculo.equals(other.idveiculo)));
    }

    @Override
    public String toString() {
        return "model.Veiculo[ idveiculo=" + idveiculo + " ]";
    }
    
}
