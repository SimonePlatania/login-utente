package com.asta.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.asta.entity.Asta;

@Mapper
public interface AstaMapper {

	    @Select("SELECT a.*, i.nome as nome_item, u.username as username_offerente " + 
	            "FROM aste a " +
	            "LEFT JOIN items i ON a.item_id = i.id " + 
	            "LEFT JOIN users u ON a.offerta_corrente_id = u.id")
	    @Results({ 
	        @Result(property = "id", column = "id"), 
	        @Result(property = "itemId", column = "item_id"),
	        @Result(property = "dataInizio", column = "data_inizio"),
	        @Result(property = "dataFine", column = "data_fine"),
	        @Result(property = "offertaCorrente", column = "offerta_corrente"),
	        @Result(property = "offertaCorrenteId", column = "offerta_corrente_id"),
	        @Result(property = "isAttiva", column = "is_attiva"), 
	        @Result(property = "stato", column = "stato"),
	        @Result(property = "nomeItem", column = "nome_item"),
	        @Result(property = "usernameOfferente", column = "username_offerente"),
	        @Result(property = "isStartNow", column = "is_start_now")
	    })
	    List<Asta> findAll();

	    @Insert("INSERT INTO aste (item_id, data_inizio, data_fine, stato, is_attiva, is_start_now) " +
	            "VALUES (#{itemId}, #{dataInizio}, #{dataFine}, #{stato}, #{isAttiva}, #{isStartNow})")
	    @Options(useGeneratedKeys = true, keyProperty = "id")
	    void insert(Asta asta);

	    @Select("SELECT a.*, i.nome as nome_item, u.username as username_offerente " + 
	            "FROM aste a " +
	            "INNER JOIN items i ON a.item_id = i.id " +  // Cambiato da LEFT JOIN a INNER JOIN
	            "LEFT JOIN users u ON a.offerta_corrente_id = u.id " +
	            "WHERE a.is_attiva = true AND a.data_fine > NOW()")
	    @Results({ 
	        @Result(property = "id", column = "id"), 
	        @Result(property = "itemId", column = "item_id"),
	        @Result(property = "dataInizio", column = "data_inizio"),
	        @Result(property = "dataFine", column = "data_fine"),
	        @Result(property = "offertaCorrente", column = "offerta_corrente"),
	        @Result(property = "offertaCorrenteId", column = "offerta_corrente_id"),
	        @Result(property = "isAttiva", column = "is_attiva"), 
	        @Result(property = "stato", column = "stato"),
	        @Result(property = "nomeItem", column = "nome_item"),
	        @Result(property = "isStartNow", column = "is_start_now"),
	        @Result(property = "usernameOfferente", column = "username_offerente")
	    })
	    List<Asta> findAsteAttive();

	    @Update("UPDATE aste SET offerta_corrente = #{offertaCorrente}, " +
	            "offerta_corrente_id = #{offertaCorrenteId} WHERE id = #{id}")
	    void updateOfferta(Asta asta);

	    @Update("UPDATE aste SET is_attiva = #{isAttiva}, stato = #{stato} WHERE id = #{id}")
	    void update(Asta asta);

	    @Select("SELECT a.*, i.nome as nome_item, u.username as username_offerente " + 
	            "FROM aste a " +
	            "LEFT JOIN items i ON a.item_id = i.id " + 
	            "LEFT JOIN users u ON a.offerta_corrente_id = u.id " +
	            "WHERE a.id = #{id}")
	    @Results({ 
	        @Result(property = "id", column = "id"), 
	        @Result(property = "itemId", column = "item_id"),
	        @Result(property = "dataInizio", column = "data_inizio"),
	        @Result(property = "dataFine", column = "data_fine"),
	        @Result(property = "offertaCorrente", column = "offerta_corrente"),
	        @Result(property = "offertaCorrenteId", column = "offerta_corrente_id"),
	        @Result(property = "isAttiva", column = "is_attiva"), 
	        @Result(property = "stato", column = "stato"),
	        @Result(property = "nomeItem", column = "nome_item"),
	        @Result(property = "isStartNow", column = "is_start_now"),
	        @Result(property = "usernameOfferente", column = "username_offerente")
	    })
	    Asta findById(@Param("id") Long id);
	    
	    @Select("SELECT * FROM aste WHERE is_attiva = true AND data_fine <= NOW()")
	    @Results({ 
	        @Result(property = "id", column = "id"), 
	        @Result(property = "itemId", column = "item_id"),
	        @Result(property = "dataInizio", column = "data_inizio"),
	        @Result(property = "dataFine", column = "data_fine"),
	        @Result(property = "offertaCorrente", column = "offerta_corrente"),
	        @Result(property = "offertaCorrenteId", column = "offerta_corrente_id"),
	        @Result(property = "isAttiva", column = "is_attiva"), 
	        @Result(property = "stato", column = "stato"),
	        @Result(property = "nomeItem", column = "nome_item"),
	        @Result(property = "isStartNow", column = "is_start_now"),
	        @Result(property = "usernameOfferente", column = "username_offerente")
	    })
	    List<Asta> findAsteScadute();

	    @Select("SELECT a.* FROM aste a " +
	            "WHERE a.offerta_corrente_id = #{userId} " +
	            "AND a.is_attiva = false " +
	            "AND a.stato = 'TERMINATA'")
	    @Results({ 
	        @Result(property = "id", column = "id"), 
	        @Result(property = "itemId", column = "item_id"),
	        @Result(property = "dataInizio", column = "data_inizio"),
	        @Result(property = "dataFine", column = "data_fine"),
	        @Result(property = "offertaCorrente", column = "offerta_corrente"),
	        @Result(property = "offertaCorrenteId", column = "offerta_corrente_id"),
	        @Result(property = "isAttiva", column = "is_attiva"), 
	        @Result(property = "stato", column = "stato"),
	        @Result(property = "nomeItem", column = "nome_item"),
	        @Result(property = "isStartNow", column = "is_start_now"),
	        @Result(property = "usernameOfferente", column = "username_offerente")
	    })
	    List<Asta> findAsteVinte(@Param("userId") Long userId);
	
	}

