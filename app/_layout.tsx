import React from "react";
import { Stack } from "expo-router";
import { ListasProvider } from "./context/ListasContext";

export default function RootLayout() {
  return (
    <ListasProvider>
      <Stack screenOptions={{ headerShown: false }}>
        {/* Tela inicial */}
        <Stack.Screen name="index" />

        {/* Telas de autenticação */}
        <Stack.Screen name="login/index" />
        <Stack.Screen name="cadastro/index" />

        {/* Layout de abas do app */}
        <Stack.Screen name="(tabs)" />
      </Stack>
    </ListasProvider>
  );
}
