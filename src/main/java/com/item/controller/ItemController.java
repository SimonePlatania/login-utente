package com.item.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.item.entity.Item;
import com.item.service.ItemService;

@RestController
@RequestMapping("/api/items")
@CrossOrigin
public class ItemController {
   private final ItemService itemService;

   public ItemController(ItemService itemService) {
       this.itemService = itemService;
   }

   @PostMapping
   public ResponseEntity<?> createItem(@RequestBody Item item, @RequestParam Long gestoreId) {
       try {
           itemService.createItem(item, gestoreId);
           return ResponseEntity.ok().build();
       } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
   }

   @GetMapping("/{id}")
   public ResponseEntity<?> getItem(@PathVariable Long id) {
       Item item = itemService.getItemById(id);
       return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
   }

   @GetMapping("/gestore/{gestoreId}")
   public ResponseEntity<?> getItemsByGestore(@PathVariable Long gestoreId) {
       return ResponseEntity.ok(itemService.findByGestoreId(gestoreId));
   }
   
   
}