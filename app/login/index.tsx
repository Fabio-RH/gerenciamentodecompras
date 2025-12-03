import React, { useState } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Alert
} from "react-native";
import { useRouter } from "expo-router";
import { login } from "../../components/api";  // <-- usa sua API corrigida

export default function Login() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");

  const router = useRouter();

  const handleLogin = async () => {
    if (!email.trim() || !senha.trim()) {
      Alert.alert("Erro", "Preencha e-mail e senha.");
      return;
    }

    const resultado = await login(email.trim(), senha.trim());

    if (!resultado) {
      Alert.alert("Erro", "Falha ao fazer login. Verifique suas credenciais.");
      return;
    }

    Alert.alert("Sucesso", "Login realizado com sucesso!");
    router.replace("/home"); // vá para a tela principal
  };

  const irParaCadastro = () => {
    router.push("/cadastro");
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Login</Text>

      <TextInput
        style={styles.input}
        placeholder="E-mail"
        autoCapitalize="none"
        value={email}
        onChangeText={setEmail}
      />

      <TextInput
        style={styles.input}
        placeholder="Senha"
        secureTextEntry
        value={senha}
        onChangeText={setSenha}
      />

      <TouchableOpacity style={styles.button} onPress={handleLogin}>
        <Text style={styles.buttonText}>Entrar</Text>
      </TouchableOpacity>

      <TouchableOpacity style={styles.cadastrar} onPress={irParaCadastro}>
        <Text style={styles.cadastrarText}>Criar conta</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f9f9f9",
    padding: 20
  },
  title: {
    fontSize: 28,
    fontWeight: "bold",
    color: "#4a2e8e",
    marginBottom: 40
  },
  input: {
    width: "80%",
    borderWidth: 1,
    borderColor: "#ccc",
    padding: 12,
    borderRadius: 10,
    backgroundColor: "#fff",
    marginBottom: 15
  },
  button: {
    backgroundColor: "#7b3fcf",
    padding: 15,
    borderRadius: 10,
    width: "80%",
    alignItems: "center",
    marginTop: 10
  },
  buttonText: {
    color: "#fff",
    fontSize: 18,
    fontWeight: "bold"
  },
  cadastrar: {
    marginTop: 20
  },
  cadastrarText: {
    color: "#4a2e8e",
    fontWeight: "bold"
  }
});
