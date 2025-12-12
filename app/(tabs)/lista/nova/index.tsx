import { View, Text, TextInput, TouchableOpacity, StyleSheet, Alert } from "react-native";
import { useState } from "react";
import { useRouter } from "expo-router";
import {useListas} from "../../../context/ListasContext";

export default function NovaLista() {
  const [nome, setNome] = useState("");
  const { adicionarLista } = useListas();
  const router = useRouter();

  const handleSalvar = () => {
    if (!nome.trim()) {
      Alert.alert("Erro", "Digite um nome para a lista!");
      return;
    }
    adicionarLista(nome.trim());
    Alert.alert("Sucesso", "Lista criada com sucesso!");
    router.back();
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Nova Lista</Text>

      <TextInput
        style={styles.input}
        placeholder="Nome da lista"
        value={nome}
        onChangeText={setNome}
      />

      <TouchableOpacity style={styles.button} onPress={handleSalvar}>
        <Text style={styles.buttonText}>Salvar</Text>
      </TouchableOpacity>

      <TouchableOpacity style={styles.cancelar} onPress={() => router.back()}>
        <Text style={styles.cancelarText}>Cancelar</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, justifyContent: "center", alignItems: "center", backgroundColor: "#f9f9f9", padding: 20 },
  title: { fontSize: 26, fontWeight: "bold", color: "#4a2e8e", marginBottom: 20 },
  input: { width: "80%", borderWidth: 1, borderColor: "#ccc", padding: 10, borderRadius: 8, marginBottom: 15 },
  button: { backgroundColor: "#7b3fcf", padding: 15, borderRadius: 8, width: "80%", alignItems: "center" },
  buttonText: { color: "#fff", fontWeight: "bold", fontSize: 16 },
  cancelar: { marginTop: 20 },
  cancelarText: { color: "#4a2e8e", fontWeight: "bold" },
});
