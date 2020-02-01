import React from "react";

class ProductRow extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isInputDisabled: true
    };
    this.deleteProduct = this.deleteProduct.bind(this);
    this.editProduct = this.editProduct.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange() {
    this.setState({ isInputDisabled: !this.state.isInputDisabled });
  }

  deleteProduct() {
    this.props.deleteProduct(this.props.product.sku);
  }

  editProduct() {
    this.setState({ isInputDisabled: true });
    this.props.editProduct({
      sku: this.props.product.sku,
      brand: this.brandInput.value,
      name: this.nameInput.value,
      price: this.priceInput.value
    });
  }

  render() {
    return (
      <tr>
        <td className="align-middle">{this.props.product.sku}</td>
        <td>
          <input
            type="text"
            ref={brandInput => (this.brandInput = brandInput)}
            className="form-control"
            defaultValue={this.props.product.brand}
            disabled={this.state.isInputDisabled}
          />
        </td>
        <td>
          <input
            type="text"
            ref={nameInput => (this.nameInput = nameInput)}
            className="form-control"
            defaultValue={this.props.product.name}
            disabled={this.state.isInputDisabled}
          />
        </td>
        <td>
          <input
            type="text"
            ref={priceInput => (this.priceInput = priceInput)}
            className="form-control"
            defaultValue={this.props.product.price}
            disabled={this.state.isInputDisabled}
          />
        </td>
        <td>
          <button
            type="button"
            className="btn btn-primary"
            onClick={this.handleChange}
          >
            Edit
          </button>
        </td>
        <td>
          <button
            type="button"
            className="btn btn-primary"
            onClick={this.editProduct}
          >
            Save
          </button>
        </td>
        <td>
          <button
            type="button"
            className="btn btn-danger"
            onClick={this.deleteProduct}
          >
            Delete
          </button>
        </td>
      </tr>
    );
  }
}

export default ProductRow;
