import { useEffect, useState } from "react";

import { useAppDispatch, useAppSelector } from "../store/hooks";
import { deleteProduct, fetchProducts } from "../store/slices/productSlice";
import { fetchRawMaterials } from "../store/slices/rawMaterialSlice";
import ProductForm from "../components/Product/ProductForm";
import type { ProductResponseType } from "../types/product";

const ProductsPage: React.FC = () => {
  const dispatch = useAppDispatch();
  const { items } = useAppSelector((s) => s.products);
  const rawMaterials = useAppSelector((s) => s.rawMaterials.items);

  const [editingProduct, setEditingProduct] =
    useState<ProductResponseType | null>(null);

  useEffect(() => {
    dispatch(fetchProducts());
    dispatch(fetchRawMaterials());
  }, [dispatch]);

  const getMaterialCode = (id: number) => {
    return rawMaterials.find((rm) => rm.id === id)?.code ?? "";
  };

  return (
    <div className="bg-white rounded-xl shadow-sm border p-6 space-y-8">
      <h1 className="text-2xl font-bold text-gray-800">
        Products
      </h1>

      <ProductForm
        key={editingProduct?.id ?? "new"}
        initialData={editingProduct ?? undefined}
        onFinish={() => setEditingProduct(null)}
      />

      {/* 🔵 LISTAGEM MELHORADA */}
      <div className="grid gap-6">
        {items.map((product) => (
          <div
            key={product.id}
            className="border rounded-xl p-6 bg-gray-50 shadow-sm hover:shadow-md transition"
          >
            {/* HEADER DO PRODUTO */}
            <div className="flex flex-col md:flex-row md:justify-between md:items-center gap-4">
              <div>
                <p className="text-sm text-gray-500">
                  Code: {product.code}
                </p>

                <h2 className="text-lg font-semibold text-gray-800">
                  {product.name}
                </h2>

                <p className="text-blue-600 font-bold mt-1">
                  ${product.price.toFixed(2)}
                </p>
              </div>

              <div className="flex gap-2">
                <button
                  onClick={() => setEditingProduct(product)}
                  className="bg-yellow-500 hover:bg-yellow-600 text-white px-4 py-2 rounded-lg transition cursor-pointer"
                >
                  Edit
                </button>

                <button
                  onClick={() => {
                    if (
                      confirm(
                        "Are you sure you want to delete this product?",
                      )
                    ) {
                      dispatch(deleteProduct(product.id));
                    }
                  }}
                  className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg transition cursor-pointer"
                >
                  Delete
                </button>
              </div>
            </div>

            {/* 🔵 RAW MATERIALS SECTION */}
            <div className="mt-5">
              <h3 className="text-sm font-semibold text-gray-600 mb-3">
                Raw Materials Required
              </h3>

              <div className="overflow-x-auto">
                <table className="w-full text-sm border-collapse">
                  <thead>
                    <tr className="bg-gray-100 text-left text-gray-600">
                      <th className="p-2">Code</th>
                      <th className="p-2">Name</th>
                      <th className="p-2">Required Qty</th>
                    </tr>
                  </thead>

                  <tbody>
                    {product.rawMaterials.map((rm) => (
                      <tr
                        key={rm.rawMaterialId}
                        className="border-t hover:bg-gray-100 transition"
                      >
                        <td className="p-2">
                          {getMaterialCode(rm.rawMaterialId)}
                        </td>
                        <td className="p-2">
                          {rm.rawMaterialName}
                        </td>
                        <td className="p-2 font-medium">
                          {rm.requiredQuantity}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductsPage;