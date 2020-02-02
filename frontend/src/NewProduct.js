import React from "react";

class NewProduct extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      sku: "",
      brand: "",
      name: "",
      price: ""
    }
  }

  handleChange = (e) => {
    var key = e.target.id;
    this.setState({ [key]: e.target.value }); 
  }
  
  createProduct = () => this.props.createProduct(this.state);

  render() {
    return (
      <tr>
        <th>
          <input
            id="sku"
            type="text"
            className="form-control"
            placeholder="sku (xxxx-xxx-xxx)"
            onChange={this.handleChange}
          />
        </th>
        <th>
          <input
            id="brand"
            type="text"
            className="form-control"
            placeholder="brand"
            onChange={this.handleChange}
          />
        </th>
        <th>
          <input
            id="name"
            type="text"
            className="form-control"
            placeholder="name"
            onChange={this.handleChange}
          />
        </th>
        <th>
          <input
            id="price"
            type="text"
            className="form-control"
            placeholder="price"
            onChange={this.handleChange}
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
