package com.item.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.item.entity.Item;
import com.item.exception.ItemNotFoundException;
import com.item.mapper.ItemMapper;
import com.login.entity.Utente;
import com.login.mapper.UtenteMapper;

@Service
public class ItemService {

	private final ItemMapper itemMapper;
	private final UtenteMapper utenteMapper;

	public ItemService(ItemMapper itemMapper, UtenteMapper utenteMapper) {
		this.itemMapper = itemMapper;
		this.utenteMapper = utenteMapper;
	}

	@Transactional
	public void createItem(Item item, Long gestoreId) {
		if (!isGestore(gestoreId)) {
			throw new RuntimeException("Accesso negato: ruolo gestore richiesto");
		}

		validateItem(item);

		item.setDataCreazione(LocalDateTime.now());
		item.setInAsta(false);
		item.setGestoreId(gestoreId);

		itemMapper.insert(item);

	}

	private boolean isGestore(Long userId) {
		Utente utente = utenteMapper.findById(userId);
		System.out.println("Utente trovato: " + utente);
		System.out.println("Ruolo: " + utente.getRuolo());
		return "GESTORE".equals(utente.getRuolo());
	}

	private void validateItem(Item item) {
		if (item.getPrezzoBase().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Il prezzo base deve essere positivo");
		}
		if (item.getRilancioMinimo().compareTo(item.getPrezzoBase()) >= 0) {
			throw new IllegalArgumentException("Il rilancio minimo deve essere inferiore al prezzo base");
		}
	}

	@Transactional
	public void updateItem(Item item, Long gestoreId) {
		Item existingItem = itemMapper.findById(item.getId());
		if (!existingItem.getGestoreId().equals(gestoreId)) {
			throw new RuntimeException("Non autorizzato a modificare questo item");
		}
		validateItem(item);
		itemMapper.update(item);
	}

	@Transactional
	public void deleteItem(Long itemId, Long gestoreId) {
		Item item = itemMapper.findById(itemId);
		if (!item.getGestoreId().equals(gestoreId)) {
			throw new RuntimeException("Non autorizzato a eliminare questo item");
		}
		itemMapper.delete(itemId);
	}

	public Item getItemById(Long id) {
		Item item = itemMapper.findById(id);
		if (item == null) {
			throw new ItemNotFoundException("Item non trovato con id: " + id);
		}
		return item;
	}

	public List<Item> findByGestoreId(Long gestoreId) {
		return itemMapper.findByGestoreId(gestoreId);
	}
}
