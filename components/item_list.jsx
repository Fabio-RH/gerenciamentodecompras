    import { useState } from "react";
import { FlatList, StyleSheet, Text, TouchableOpacity, View } from "react-native";



 const ListaItem = ({ item}) => {

      return (
        <View style={styles.cardItemRed}>
          <TouchableOpacity>
            <Text style={styles.cardText}>{item.item_nome} - Quantidade: {item.item_quantidade} unidades</Text>
          </TouchableOpacity>
        </View>
      );
    };

    const ItemList = ({ lista}) => {
        return(
            <FlatList
                data={lista}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => <ListaItem  item={item} />}
            />
        )
    }



    const styles = StyleSheet.create({
      listContainer: {
        paddingTop: 20,
      },
      itemContainer: {
        backgroundColor: "#f9f9f9",
        padding: 15,
        marginVertical: 8,
        marginHorizontal: 16,
        borderRadius: 8,
        elevation: 2,
      },
      itemTouchable: {
        paddingVertical: 10,
      },
      itemTitle: {
        fontSize: 19,
        fontWeight: "bold",
      },
      itemContent: {
        fontSize: 16,
        marginTop: 10,
      },
    cardItemRed: {
        backgroundColor: 'rgba(107, 182, 69, 1)',
        borderRadius: 6,
        padding: 12,
        marginBottom: 10,
    },
    cardText: {
        color: '#FFF',
        fontSize: 14,
        fontWeight: '500',
    }
    });

export default ItemList;