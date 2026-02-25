import { useEffect } from "react";

import { useAppDispatch, useAppSelector } from "../store/hooks";
import { fetchProducts } from "../store/slices/productSlice";
import ProductForm from "../components/Product/ProductForm";

const ProductsPage = () => {
  const dispatch = useAppDispatch();
  const { items } = useAppSelector((s) => s.products);

  useEffect(() => {
    dispatch(fetchProducts());
  }, [dispatch]);

  return (
    <div className="bg-white rounded-xl shadow-sm border p-6 space-y-6">
      <h1 className="text-2xl font-bold text-gray-800">
      Products
      </h1>

      <ProductForm />

      <div className="space-y-3">
        {items.map((p) => (
          <div
            key={p.id}
            className="border rounded-lg p-4 flex justify-between shadow-sm hover:shadow-md transition"
          >
            <span className="font-medium">{p.name}</span>
            <span className="text-gray-600">${p.price}</span>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductsPage;