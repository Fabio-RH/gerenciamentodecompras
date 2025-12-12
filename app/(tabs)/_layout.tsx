import { Tabs } from "expo-router";

export default function TabsLayout() {
  return (
    <Tabs screenOptions={{ headerShown: false }}>
      <Tabs.Screen name="index" options={{ title: "Minhas Listas" }} />
      <Tabs.Screen name="lista/index" options={{ title: "Listas" }} />
      <Tabs.Screen name="lista/nova/index" options={{ title: "Nova Lista" }} />
      <Tabs.Screen name="lista/[id]" options={{ title: "Detalhes" }} />
    </Tabs>
  );
}
