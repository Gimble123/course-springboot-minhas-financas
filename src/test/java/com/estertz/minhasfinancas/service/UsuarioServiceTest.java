package com.estertz.minhasfinancas.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.estertz.minhasfinancas.exception.ErroAutenticacao;
import com.estertz.minhasfinancas.exception.RegraNegocioException;
import com.estertz.minhasfinancas.model.entity.Usuario;
import com.estertz.minhasfinancas.model.repository.UsuarioRepository;
import com.estertz.minhasfinancas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@SpyBean
	UsuarioServiceImpl service;
	
	@MockBean
	UsuarioRepository repository;
	
	
	@Test
	public void deveSalvarUmUsuario() {
		//cenário
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder()
				.id(1l)
				.nome("nome")
				.email("email@email.com")
				.senha("senha")
				.build();
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		//acao
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
		
		
		
		//verificacao
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
		
	}
	
	
	
	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		//cenário
		String email = "email@email.com";
		String senha = "senha";
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//acao
		Usuario result = service.autenticar("email@email.com", "senha");
		
		//verificação
		Assertions.assertThat(result).isNotNull();
	}
	
	//Não funciona
	
	@Test
	public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		
		//cenário
		String email = "email@teste.com";
		Usuario usuario = Usuario.builder().email(email).build();
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		//acao
		org.junit.jupiter.api.Assertions.assertThrows(RegraNegocioException.class, () ->  service.salvarUsuario(usuario));
		
		//verificacao
		Mockito.verify( repository, Mockito.never() ).save(usuario);
	
	}
	
	
	
	@Test
	public void deveLancarQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
		//cenário
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
			
		//ação
		 Throwable exception =  Assertions.catchThrowable(() ->  service.autenticar("teste@email.com", "senha") );
		 
		 //verificação
		 Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class);
	}
	
	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		//cenário
		
		Usuario usuario = Usuario.builder().email("email@email.com").senha("senha").build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		//ação
		Throwable exception =  Assertions.catchThrowable( () -> service.autenticar("email@email.com", "123") );
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class);
	}
	
	@Test
	public void deveValidarEmail() {
			// cenario
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
			
			repository.deleteAll();
 
			// acao
			service.validarEmail("enail@email.com");
	}
 
	
	
}
