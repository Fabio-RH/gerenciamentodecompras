import React, { useState } from "react";
import { View, Text, FlatList, TouchableOpacity, StyleSheet, TextInput, Alert } from "react-native";
import { useLocalSearchParams, useRouter } from "expo-router";
import { useListas } from "../../context/ListasContext";

export default function ListaDetalhe() {
  const { id } = useLocalSearchParams();
  const router = useRouter();
  const { getLista, toggleItem, excluirItem, adicionarItem } = useListas();
  const lista = getLista(String(id));

  const [novoItem, setNovoItem] = useState("");

  if (!lista) {
    return (
      <View style={styles.container}>
        <Text>Lista não encontrada.</Text>
      </View>
    );
  }

  const handleAddItem = () => {
    if (!novoItem.trim()) {
      Alert.alert("Erro", "Digite o nome do item!");
      return;
    }
    adicionarItem(lista.id, novoItem.trim());
    setNovoItem("");
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>{lista.nome}</Text>

      <FlatList
        data={lista.itens}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <View style={styles.itemRow}>
            <TouchableOpacity onPress={() => toggleItem(lista.id, item.id)}>
              <Text
                style={[
                  styles.itemText,
                  { textDecorationLine: item.comprado ? "line-through" : "none" },
                ]}
              >
                {item.nome}
              </Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={() => excluirItem(lista.id, item.id)}>
              <Text style={styles.deleteText}>🗑️</Text>
            </TouchableOpacity>
          </View>
        )}
      />

      {/* Input para adicionar item sem sair da tela */}
      <View style={styles.inputRow}>
        <TextInput
          style={styles.input}
          placeholder="Novo item"
          value={novoItem}
          onChangeText={setNovoItem}
        />
        <TouchableOpacity style={styles.addButton} onPress={handleAddItem}>
          <Text style={styles.addButtonText}>+</Text>
        </TouchableOpacity>
      </View>

      <TouchableOpacity
        style={styles.backButton}
        onPress={() => router.back()}
      >
        <Text style={styles.backText}>Voltar</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, padding: 20, backgroundColor: "#f9f9f9" },
  title: { fontSize: 26, fontWeight: "bold", color: "#4a2e8e", marginBottom: 20 },
  itemRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    backgroundColor: "#fff",
    padding: 15,
    borderRadius: 8,
    marginBottom: 10,
  },
  itemText: { fontSize: 18 },
  deleteText: { color: "red", fontSize: 18 },
  inputRow: { flexDirection: "row", marginTop: 10 },
  input: {
    flex: 1,
    borderWidth: 1,
    borderColor: "#ccc",
    backgroundColor: "#fff",
    padding: 10,
    borderRadius: 8,
  },
  addButton: {
    marginLeft: 10,
    backgroundColor: "#4a2e8e",
    borderRadius: 8,
    padding: 10,
    justifyContent: "center",
    alignItems: "center",
  },
  addButtonText: { color: "#fff", fontSize: 18, fontWeight: "bold" },
  backButton: {
    marginTop: 20,
    backgroundColor: "#aaa",
    padding: 12,
    borderRadius: 8,
    alignItems: "center",
  },
  backText: { color: "#fff", fontWeight: "bold" },
});
