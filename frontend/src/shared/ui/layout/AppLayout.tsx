"use client";

import { Box, Container } from "@chakra-ui/react";
import { Navbar } from "./Navbar";

export function AppLayout({ children }: { children: React.ReactNode }) {
  return (
    <>
      <Navbar />
      <Box as="main" py={8}>
        <Container maxW="container.lg">{children}</Container>
      </Box>
    </>
  );
}
