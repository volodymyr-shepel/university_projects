using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Lab10.Migrations
{
    /// <inheritdoc />
    public partial class ModifyImagePathColumn : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Image",
                table: "Articles");

            migrationBuilder.AddColumn<string>(
                name: "ImageFileName",
                table: "Articles",
                type: "nvarchar(max)",
                nullable: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "ImageFileName",
                table: "Articles");

            migrationBuilder.AddColumn<byte[]>(
                name: "Image",
                table: "Articles",
                type: "varbinary(max)",
                nullable: true);
        }
    }
}
