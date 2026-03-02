"use client";

import { Badge, Box, Card, CardBody } from "@chakra-ui/react";
import { Table, Column } from "@/shared/ui/Table";

type AvailabilityResponse = {
  carId: string;
  model: string;
  isAvailable: boolean;
  estimatedPrice?: number;
  conflicts?: boolean;
};

interface AvailabilityTableProps {
  results: AvailabilityResponse[];
  isLoading: boolean;
}

export function AvailabilityTable({
  results,
  isLoading,
}: AvailabilityTableProps) {
  const columns: Column<AvailabilityResponse>[] = [
    { header: "Car ID", render: (car: AvailabilityResponse) => car.carId },
    {
      header: "Model",
      render: (car: AvailabilityResponse) => (
        <Box fontWeight="medium">{car.model}</Box>
      ),
    },
    {
      header: "Status",
      render: (car: AvailabilityResponse) => (
        <Badge colorScheme={car.isAvailable ? "green" : "red"}>
          {car.isAvailable ? "Available" : "Unavailable"}
        </Badge>
      ),
    },
    {
      header: "Estimated Price",
      isNumeric: true,
      render: (car: AvailabilityResponse) =>
        car.estimatedPrice ? `$${car.estimatedPrice}` : "N/A",
    },
    {
      header: "Conflicts",
      render: (car: AvailabilityResponse) =>
        car.conflicts ? <Badge colorScheme="orange">Yes</Badge> : "No",
    },
  ];

  if (!isLoading && results.length === 0) return null;

  return (
    <Card variant="outline">
      <CardBody p={0}>
        <Table
          columns={columns}
          data={results}
          getRowId={(car) => car.carId}
          isLoading={isLoading}
          variant={isLoading ? "simple" : "striped"}
          colorScheme={isLoading ? undefined : "gray"}
        />
      </CardBody>
    </Card>
  );
}
