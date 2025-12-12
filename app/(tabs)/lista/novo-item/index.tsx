import React, { useState } from "react";
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Alert } from "react-native";
import { useRouter, useLocalSearchParams } from "expo-router";
import { useListas } from "../../../context/ListasContext";

export default function NovoItem() {
  const { listaId } = useLocalSearchParams(); // pega id da lista da rota
  const { getLista, adicionarItem } = useListas();
  const lista = getLista(String(listaId));

  const [nomeItem, setNomeItem] = useState("");
  const router = useRouter();

  const handleSalvar = () => {
    if (!nomeItem.trim()) {
      Alert.alert("Erro", "Digite o nome do item antes de salvar!");
      return;
    }
    if (!lista) {
      Alert.alert("Erro", "Lista não encontrada!");
      return;
    }

    adicionarItem(lista.id, nomeItem.trim());
    Alert.alert("Sucesso", "Item adicionado com sucesso!");
    router.replace(`/(tabs)/lista/${lista.id}`); // volta para detalhes da lista
  };

  return (
    <View style={styles.container}>
      <Text style={styles.titulo}>Adicionar Novo Item</Text>
      <TextInput
        placeholder="Nome do item"
        value={nomeItem}
        onChangeText={setNomeItem}
        style={styles.input}
      />
      <TouchableOpacity style={styles.botao} onPress={handleSalvar}>
        <Text style={styles.textoBotao}>Salvar</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={[styles.botao, { backgroundColor: "#aaa" }]}
        onPress={() => router.back()}
      >
        <Text style={styles.textoBotao}>Cancelar</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#f2f2f2", padding: 20, justifyContent: "center" },
  titulo: { fontSize: 22, fontWeight: "bold", marginBottom: 20, color: "#4a2e8e", textAlign: "center" },
  input: { borderWidth: 1, borderColor: "#ccc", borderRadius: 10, padding: 12, fontSize: 16, backgroundColor: "#fff", marginBottom: 20 },
  botao: { backgroundColor: "#7b3fcf", padding: 15, borderRadius: 10, alignItems: "center", marginBottom: 10 },
  textoBotao: { color: "#fff", fontSize: 18, fontWeight: "bold" },
});
