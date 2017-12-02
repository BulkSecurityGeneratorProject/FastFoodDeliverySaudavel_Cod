package com.fastfooddelivery.web.rest.vm;

import com.fastfooddelivery.service.dto.UserDTO;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
    
    private String celular;
    
    private LocalDate dataNascimento;
    
    private String sexo;
    
    private BigDecimal peso;
    
    private BigDecimal altura;
    
    private String cep;
    
    private String endereco;
    
    private Integer numero;
    
    private String complemento;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public ManagedUserVM(Long id, String login, String password, String firstName, String lastName,
                         String email, boolean activated, String imageUrl, String langKey,
                         String createdBy, Instant createdDate, String lastModifiedBy, Instant lastModifiedDate,
                        Set<String> authorities, String celular, LocalDate dataNascimento, String sexo, BigDecimal peso,
                        BigDecimal altura, String cep, String endereco, Integer numero, String complemento) {

        super(id, login, firstName, lastName, email, activated, imageUrl, langKey,
            createdBy, createdDate, lastModifiedBy, lastModifiedDate,  authorities);
        this.password = password;
        this.celular = celular;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.peso = peso;
        this.altura = altura; 
        this.cep = cep; 
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
    }

    public String getPassword() {
        return password;
    }
    
    public String getCelular() {
		return celular;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public BigDecimal getAltura() {
		return altura;
	}

	public String getCep() {
		return cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public Integer getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	@Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }
}
