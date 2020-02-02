import React from "react";

class NewProduct extends React.Component {
  createProduct = () => {
    this.props.createProduct({
      sku: this.skuInput.value,
      brand: this.brandInput.value,
      name: this.nameInput.value,
      price: this.priceInput.value
    });
  }

  render() {
    return (
      <tr>
        <th>
          <input
            type="text"
            ref={skuInput => (this.skuInput = skuInput)}
            className="form-control"
            placeholder="sku (xxxx-xxx-xxx)"
          />
        </th>
        <th>
          <input
            type="text"
            ref={brandInput => (this.brandInput = brandInput)}
            className="form-control"
            placeholder="brand"
          />
        </th>
        <th>
          <input
            type="text"
            ref={nameInput => (this.nameInput = nameInput)}
            className="form-control"
            placeholder="name"
          />
        </th>
        <th>
          <input
            type="text"
            ref={priceInput => (this.priceInput = priceInput)}
            className="form-control"
            placeholder="price"
          />
        </th>
        <th colSpan="3">
          <button
            type="button"
            id="add-product-btn"
            className="btn btn-primary"
            onClick={this.createProduct}
          >
            Add
          </button>
        </th>
      </tr>
    );
  }
}

export default NewProduct;
