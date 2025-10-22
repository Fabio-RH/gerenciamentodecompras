import React, { createContext, ReactNode, useContext, useState } from "react";

type Lista = {
  id: string;
  nome: string;
  // A tipagem de 'itens' deve ser de string[], conforme você usa no seu [id].tsx.
  // No [id].tsx, você mapeia 'lista.itens', então o tipo de Item na lista
  // deve ser o mesmo que o tipo de 'itens' na Lista.
  // No seu [id].tsx, a lista.itens é usada em <FlatList data={lista.itens} ...>,
  // e renderItem espera objetos com 'id', 'nome' e 'comprado'.
  // Vamos adaptar a tipagem de Lista para refletir isso.
  // SEUS OUTROS CÓDIGOS SUGEREM QUE 'itens' É UM ARRAY DE OBJETOS, não de strings.
  // Vou usar uma tipagem que reflete o uso em [id].tsx:
  itens: {
    id: string;
    nome: string;
    comprado: boolean;
  }[];
};

type ListasContextType = {
  listas: Lista[];
  // 🟢 MUDANÇA AQUI: Agora retorna o ID (string)
  adicionarLista: (nome: string) => string; 
  editarLista: (id: string, nome: string) => void;
  removerLista: (id: string) => void;
  getLista: (id: string) => Lista | undefined;
  // Se você tiver outras funções como toggleItem, adicionarItem etc., 
  // adicione-as aqui também para o TS funcionar corretamente no [id].tsx
  toggleItem?: (listaId: string, itemId: string) => void;
  adicionarItem?: (listaId: string, nome: string) => void;
  editarItem?: (listaId: string, itemId: string, novoNome: string) => void;
  excluirItem?: (listaId: string, itemId: string) => void;
};

const ListasContext = createContext<ListasContextType | undefined>(undefined);

export function ListasProvider({ children }: { children: ReactNode }) {
  const [listas, setListas] = useState<Lista[]>([]);

  // 🟢 MUDANÇA AQUI: Retorna o ID da nova lista
  const adicionarLista = (nome: string): string => {
    const novaListaId = Date.now().toString(); // Gerar o ID antes
    const novaLista: Lista = {
      id: novaListaId,
      nome,
      itens: [],
    };
    setListas((prev) => [...prev, novaLista]);
    
    return novaListaId; // 🟢 Retorna o ID
  };
  
  // (Funções de Item estão faltando, mas são necessárias para [id].tsx e novo-item.tsx)
  // Deixando o esqueleto de exemplo que você chamou:

  const toggleItem = (listaId: string, itemId: string) => {
    setListas(prev =>
      prev.map(lista =>
        lista.id === listaId
          ? {
              ...lista,
              itens: lista.itens.map(item =>
                item.id === itemId
                  ? { ...item, comprado: !item.comprado }
                  : item
              ),
            }
          : lista
      )
    );
  };

  const adicionarItem = (listaId: string, nome: string) => {
    const novoItem = { id: Date.now().toString(), nome, comprado: false };
    setListas(prev =>
      prev.map(lista =>
        lista.id === listaId
          ? { ...lista, itens: [...lista.itens, novoItem] }
          : lista
      )
    );
  };
  
  const editarItem = (listaId: string, itemId: string, novoNome: string) => {
    setListas(prev =>
      prev.map(lista =>
        lista.id === listaId
          ? {
              ...lista,
              itens: lista.itens.map(item =>
                item.id === itemId ? { ...item, nome: novoNome } : item
              ),
            }
          : lista
      )
    );
  };

  const excluirItem = (listaId: string, itemId: string) => {
    setListas(prev =>
      prev.map(lista =>
        lista.id === listaId
          ? { ...lista, itens: lista.itens.filter(item => item.id !== itemId) }
          : lista
      )
    );
  };

  // Funções existentes (sem alteração lógica, mas re-tipadas)
  const editarLista = (id: string, nome: string) => {
    setListas((prev) =>
      prev.map((lista) => (lista.id === id ? { ...lista, nome } : lista))
    );
  };

  const removerLista = (id: string) => {
    setListas((prev) => prev.filter((lista) => lista.id !== id));
  };

  const getLista = (id: string) => listas.find((lista) => lista.id === id);

  return (
    <ListasContext.Provider
      value={{ 
        listas, 
        adicionarLista, 
        editarLista, 
        removerLista, 
        getLista,
        toggleItem, // 💡 Incluindo funções de item
        adicionarItem,
        editarItem,
        excluirItem,
      }}
    >
      {children}
    </ListasContext.Provider>
  );
}

export function useListas() {
  const context = useContext(ListasContext);
  if (!context) {
    throw new Error("useListas deve ser usado dentro de um ListasProvider");
  }
  // ⚠️ Importante: O seu uso em [id].tsx e novo-item.tsx exige que todas
  // as funções (adicionarLista, toggleItem, etc.) estejam no Context.
  // Você fez um "as any" nos outros arquivos. Com a tipagem acima, 
  // você pode remover o "as any" nesses locais (melhorando o código).
  return context as ListasContextType; // Retorna o contexto tipado
}