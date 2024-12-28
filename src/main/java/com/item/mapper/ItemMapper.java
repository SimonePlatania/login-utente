package com.item.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;
import com.item.entity.Item;

@Mapper
public interface ItemMapper {

	@Select("SELECT i.*, u.username as gestore_username FROM items i " + "LEFT JOIN users u ON i.gestore_id = u.id")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "gestoreId", column = "gestore_id"),
			@Result(property = "gestoreUsername", column = "gestore_username") })
	List<Item> findAll();

	@Select("SELECT * FROM items WHERE id = #{id}")
	@Results({
	    @Result(property = "id", column = "id"),
	    @Result(property = "nome", column = "nome"),
	    @Result(property = "descrizione", column = "descrizione"),
	    @Result(property = "prezzoBase", column = "prezzo_base"),
	    @Result(property = "rilancioMinimo", column = "rilancio_minimo"),
	    @Result(property = "dataCreazione", column = "data_creazione"),
	    @Result(property = "inAsta", column = "in_asta"),
	    @Result(property = "gestoreId", column = "gestore_id"),
	    @Result(property = "gestoreUsername", column = "gestore_username",
	        one = @One(select = "com.login.mapper.UtenteMapper.findUsernameById"))
	})
	Item findById(@Param("id") Long id);

	@Insert("INSERT INTO items (nome, descrizione, prezzo_base, rilancio_minimo, "
			+ "data_creazione, in_asta, gestore_id) VALUES "
			+ "(#{nome}, #{descrizione}, #{prezzoBase}, #{rilancioMinimo}, "
			+ "#{dataCreazione}, #{inAsta}, #{gestoreId})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Item item);

	@Update("UPDATE items SET nome = #{nome}, descrizione = #{descrizione}, "
			+ "prezzo_base = #{prezzoBase}, rilancio_minimo = #{rilancioMinimo}, "
			+ "in_asta = #{inAsta} WHERE id = #{id}")
	void update(Item item);

	@Delete("DELETE FROM items WHERE id = #{id}")
	void delete(@Param("id") Long id);

	@Select("SELECT COUNT(*) FROM offerte WHERE item_id = #{itemId}")
	int countOfferte(@Param("itemId") Long itemId);

	@Select("SELECT i.*, u.username as gestore_username FROM items i JOIN users u ON i.gestore_id = u.id WHERE i.gestore_id = #{gestoreId}")
	@Results({
	    @Result(property = "id", column = "id"),
	    @Result(property = "nome", column = "nome"),
	    @Result(property = "descrizione", column = "descrizione"),
	    @Result(property = "prezzoBase", column = "prezzo_base"),
	    @Result(property = "rilancioMinimo", column = "rilancio_minimo"),
	    @Result(property = "dataCreazione", column = "data_creazione"),
	    @Result(property = "inAsta", column = "in_asta"),
	    @Result(property = "gestoreId", column = "gestore_id"),
	    @Result(property = "gestoreUsername", column = "gestore_username")
	})
	List<Item> findByGestoreId(@Param("gestoreId") Long gestoreId);
	
}