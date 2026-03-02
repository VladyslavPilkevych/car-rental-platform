import {
  Table as ChakraTable,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  Skeleton,
} from "@chakra-ui/react";
import { ReactNode } from "react";

export type Column<T> = {
  header: string;
  render: (item: T) => ReactNode;
  isNumeric?: boolean;
};

interface TableProps<T> {
  columns: Column<T>[];
  data: T[];
  getRowId: (item: T) => string | number;
  isLoading?: boolean;
  skeletonRows?: number;
  variant?: string;
  colorScheme?: string;
}

export function Table<T>({
  columns,
  data,
  getRowId,
  isLoading,
  skeletonRows = 5,
  variant = "simple",
  colorScheme,
}: TableProps<T>) {
  return (
    <TableContainer>
      <ChakraTable variant={variant} colorScheme={colorScheme} size="md">
        <Thead>
          <Tr>
            {columns.map((column) => (
              <Th key={column.header} isNumeric={column.isNumeric}>
                {column.header}
              </Th>
            ))}
          </Tr>
        </Thead>
        <Tbody>
          {isLoading
            ? Array.from({ length: skeletonRows }).map((_, rowIndex) => (
                <Tr key={`skeleton-${rowIndex}`}>
                  {columns.map((column, colIndex) => (
                    <Td
                      key={`${column.header}-${colIndex}`}
                      isNumeric={column.isNumeric}
                    >
                      <Skeleton height="20px" width="full" />
                    </Td>
                  ))}
                </Tr>
              ))
            : data.map((item) => (
                <Tr key={getRowId(item)}>
                  {columns.map((column) => (
                    <Td key={column.header} isNumeric={column.isNumeric}>
                      {column.render(item)}
                    </Td>
                  ))}
                </Tr>
              ))}
        </Tbody>
      </ChakraTable>
    </TableContainer>
  );
}
