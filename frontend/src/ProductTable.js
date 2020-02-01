import React from "react";
import ProductRow from "./Product";
import NewProduct from "./NewProduct";

const ProductTable = props => {
  return (
    <table className="table table-hover" id="products-table">
      <thead>
        <NewProduct createProduct={props.createProduct} />
      </thead>
      <tbody>
        {props.products.map(product => (
          <ProductRow
            product={product}
            key={product.sku}
            deleteProduct={props.deleteProduct}
            editProduct={props.editProduct}
          />
        ))}
      </tbody>
    </table>
  );
};

export default ProductTable;
