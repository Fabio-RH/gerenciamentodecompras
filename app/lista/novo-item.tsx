import { useLocalSearchParams, useRouter } from "expo-router";
import { useState } from "react";
import { StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";
import { useListas } from "../../context/ListasContext";

export default function NovoItem() {
  const router = useRouter();
  const { listaId, itemId, nome: nomeInicial } = useLocalSearchParams<{
    listaId: string;
    itemId?: string;
    nome?: string;
  }>();

  const { adicionarItem, editarItem } = useListas() as any;
  const [nome, setNome] = useState(nomeInicial || "");

  const salvar = () => {
    if (!nome.trim()) return alert("Digite o nome do item!");

    if (itemId) {
      editarItem(listaId, itemId, nome);
    } else {
      adicionarItem(listaId, nome);
    }

    router.back();
  };

  return (
    <View style={styles.container}>
      <Text style={styles.label}>Nome do Item:</Text>
      <TextInput
        style={styles.input}
        placeholder="Ex: Arroz"
        value={nome}
        onChangeText={setNome}
      />
      <TouchableOpacity style={styles.button} onPress={salvar}>
        <Text style={styles.buttonText}>{itemId ? "Salvar Alterações" : "Adicionar Item"}</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, padding: 20, backgroundColor: "#e7cfcf" },
  label: { fontSize: 16, marginBottom: 8 },
  input: {
    backgroundColor: "#fff",
    borderRadius: 8,
    padding: 12,
    fontSize: 16,
    marginBottom: 20,
  },
  button: {
    backgroundColor: "#7b3fcf",
    padding: 15,
    alignItems: "center",
    borderRadius: 8,
  },
  buttonText: { color: "#fff", fontSize: 16, fontWeight: "bold" },
});
