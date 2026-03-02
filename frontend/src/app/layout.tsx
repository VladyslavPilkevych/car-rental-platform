import { Providers } from "@/shared/providers/Providers";
import { AppLayout } from "@/shared/ui/layout/AppLayout";

export const metadata = {
  title: "Car Rental Platform",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>
        <Providers>
          <AppLayout>{children}</AppLayout>
        </Providers>
      </body>
    </html>
  );
}
