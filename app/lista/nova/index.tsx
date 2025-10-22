// app/lista/nova/index.tsx
import { useLocalSearchParams, useRouter } from "expo-router";
import { useEffect, useState } from "react";
import { StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";
import { useListas } from "../../../context/ListasContext"; // ✅ Cuidado com o caminho relativo!

export default function NovaLista() {
  const router = useRouter();
  const params = useLocalSearchParams<{ id?: string; nome?: string }>();

  // A função useListas já foi corrigida para retornar o ID
  const { adicionarLista, editarLista } = useListas(); 

  const [nome, setNome] = useState("");

  useEffect(() => {
    if (params.nome) setNome(params.nome);
  }, [params.nome]);

  const salvar = () => {
    if (!nome.trim()) return alert("Digite um nome para a lista!");

    let novaListaId: string;

    if (params.id) {
      // Editar lista existente
      editarLista(params.id, nome);
      alert("Lista atualizada!");
      novaListaId = params.id;
    } else {
      // Criar nova lista
      // 🟢 CHAMA A FUNÇÃO E O ID É ATRIBUÍDO CORRETAMENTE
      novaListaId = adicionarLista(nome); 
      alert("Lista criada!");
    }

    // ✅ Redireciona direto para a lista criada
    // Agora que você está em 'app/lista/nova/index.tsx', a navegação para `/lista/${id}` está correta
    router.push(`/lista/${novaListaId}`); 
  };

  return (
    <View style={styles.container}>
      <Text style={styles.label}>Nome da Lista:</Text>
      <TextInput
        style={styles.input}
        value={nome}
        onChangeText={setNome}
        placeholder="Ex: Compras da Semana"
      />
      <TouchableOpacity style={styles.button} onPress={salvar}>
        <Text style={styles.buttonText}>{params.id ? "Salvar Alterações" : "Criar Lista"}</Text>
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