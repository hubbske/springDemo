import React from "react";
import "./App.css";
import ProductTable from "./ProductTable";
import { sendDelete, sendPut, sendPost } from "./utils";

class MyComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      products: []
    };
    this.deleteProduct = this.deleteProduct.bind(this);
    this.editProduct = this.editProduct.bind(this);
    this.createProduct = this.createProduct.bind(this);
  }

  componentDidMount() {
    fetch("/products")
      .then(res => res.json())
      .then(
        result => {
          this.setState({
            isLoaded: true,
            products: result
          });
        },
        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        error => {
          this.setState({
            isLoaded: true,
            error
          });
        }
      );
  }

  deleteProduct(sku) {
    sendDelete(sku);
    this.setState({
      products: this.state.products.filter(product => product.sku !== sku)
    });
  }

  editProduct(product) {
    sendPut(JSON.stringify(product));
  }

  createProduct(product) {
    sendPost(JSON.stringify(product));
  }

  render() {
    const { error, isLoaded, products } = this.state;
    if (error) {
      return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
      return <div>Loading...</div>;
    } else {
      return (
        <ProductTable
          products={products}
          deleteProduct={this.deleteProduct}
          editProduct={this.editProduct}
          createProduct={this.createProduct}
        />
      );
    }
  }
}

function App() {
  return <MyComponent />;
}

export default App;
