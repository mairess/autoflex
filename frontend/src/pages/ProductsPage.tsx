import { useEffect, useState } from "react";

import { useAppDispatch, useAppSelector } from "../store/hooks";
import { deleteProduct, fetchProducts } from "../store/slices/productSlice";
import ProductForm from "../components/Product/ProductForm";
import type { ProductResponseType } from "../types/product";

const ProductsPage: React.FC = () => {
  const dispatch = useAppDispatch();
  const { items } = useAppSelector((s) => s.products);
  const [editingProduct, setEditingProduct] = useState<ProductResponseType | null>(null);

  useEffect(() => {
    dispatch(fetchProducts());
  }, [dispatch]);

  return (
    <div className="bg-white rounded-xl shadow-sm border p-6 space-y-6">
      <h1 className="text-2xl font-bold text-gray-800">
      Products
      </h1>

      <ProductForm
        key={editingProduct?.id ?? "new"}
        initialData={editingProduct ?? undefined}
        onFinish={() => setEditingProduct(null)}
      />

      <div className="space-y-3">
        {items.map((p) => (
          <div
            key={p.id}
            className="border rounded-lg p-4 flex justify-between items-center shadow-sm hover:shadow-md transition"
          >
            <div>
              <p className="font-medium">{p.name}</p>
              <p className="text-gray-600">${p.price}</p>
            </div>

            <div className="flex gap-2">
              <button
                onClick={() => setEditingProduct(p)}
                className="bg-yellow-500 hover:bg-yellow-600 text-white px-3 py-1 rounded-lg transition"
              >
    Edit
              </button>

              <button
                onClick={() => {
                  if (confirm("Are you sure you want to delete this product?")) {
                    dispatch(deleteProduct(p.id));
                  }
                }}
                className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded-lg transition"
              >
    Delete
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductsPage;